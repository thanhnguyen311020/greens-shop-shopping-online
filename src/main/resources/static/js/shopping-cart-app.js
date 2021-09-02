var app = angular.module("shopping-cart-app", ["ngAnimate", "ngSanitize", 'ngToast']);

app.controller("shopping-cart-ctrl", function ($scope, $http, ngToast) {
    // Quan ly gio hang
    $scope.cart = {
        items: [],
        // Them vao gio
        add(id) {
            var item = this.items.find(item => item.id == id);
            if (item) {
                item.qty++;
                this.saveToLocalStorage();
            }
            else {
                $http.get(`/greens/rest/product/${id}`).then(resp => {
                    resp.data.qty = 1;
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                })
            }
            // ngToast.dismiss();
            ngToast.create({
                className: 'success',
                content: 'Đã thêm vào giỏ hàng',
                dismissButton: true,
                animation: 'slide'
              });
        },
        // xoa khoi gio hang
        remove(id) {
            var index = this.items.findIndex(item => item.id == id);
            this.items.splice(index, 1);
            this.saveToLocalStorage();
            ngToast.create({
                className: 'success',
                content: 'Đã xóa khỏi giỏ hàng',
                dismissButton: true,
                animation: 'slide'
              });
        },
        //xoa sach cac mat hang
        clear() {
            this.items = [];
            this.saveToLocalStorage();
        },
        // Tính thành tiền các mặt hàng
        amt_of(item) { },
        // Tính tổng số lượng hàng trong giỏ
        get count() {
            return this.items
                .map(item => item.qty)
                .reduce((total, qty) => total += qty, 0);
        },
        // Tính Tổng tièn
        get amount() {
            return this.items
                .map(item => item.qty * (item.price * (( 100 - item.discount) / 100)))
                .reduce((total, qty) => total += qty, 0);
        },
        get discounts() {
            return this.items
                .map(item => item.price - (item.price*((100-item.discount)/100)))
                .reduce((total, qty) => total += qty, 0);
        },
        
        //Lưu giỏ hàng vào local storage
        saveToLocalStorage() {
            var json = JSON.stringify(angular.copy(this.items));
           
            localStorage.setItem("cart", json);
        },

        loadFormLocalStorage() {
            var json = localStorage.getItem("cart");

            this.items = json ? JSON.parse(json) : [];
        }



    }
    $scope.link = "";
    $scope.cart.loadFormLocalStorage();
    var today = new Date();
    var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
    var time = today.getHours() + ':' + today.getMinutes() + ':' + today.getSeconds();
    // console.log(date + ' ' + time);
    $scope.order = {
        createDate: Date.parse(date + ' ' + time),
        address: '',
        phone: '',
        amount: $scope.cart.amount,

        account: { username: $("#username").val() },
        get orderDetails() {
            return $scope.cart.items.map(item => {
                return {
                    product: { id: item.id },
                    price: (item.price * ((100-item.discount) / 100)) * item.qty,
                    quantity: item.qty
                }
            });
        },
        purchase() {
            var order = angular.copy(this);
            console.log(order)
            $http.post("/greens/rest/order", order).then(resp => {
                Swal.fire(
                    'Đặt hàng thành công',
                    '',
                    'warning',
                    location.href = "/greens/profile/" + resp.data.username
                )
                $scope.cart.clear();
                $scope.link = "/greens/order/detail/" + resp.data.id;
                location.href = "/greens/order/purchaseSuccess";
            }).catch(error => {
                Swal.fire(
                    'Lỗi khi đặt hàng',
                    '',
                    'error',
                    location.href = "/greens/profile/" + resp.data.username
                )
                console.log(error);
            })
        }
    }

    // Profile

    $scope.form = {};
    $scope.username = $("#username").val();

    console.log("Username: ", $scope.username)
    $scope.getAccount = function () {
        if ($scope.username == undefined) {
            $scope.username = "";
        }
        $http.get(`/greens/rest/account/${$scope.username}`).then(resp => {
            $scope.form = resp.data;
            console.log($scope.form);

        }).catch(error => {
            console.error("Error Get: ", error)
        })

    }

    $scope.getAccount();

    $scope.update = function () {
        var item = angular.copy($scope.form);
        item.password = '';
        console.log("Account: ", item)
        $http.put(`/greens/rest/account/${item.username}`, item).then(resp => {
            console.log(resp.data);
            Swal.fire(
                'Cập nhập thành công',
                '',
                'success',
                location.href = "/greens/profile/" + resp.data.username
            )
           
        }).catch(error => {
            console.log("Error: ", error);
            Swal.fire(
                'Cập nhập thất bại',
                'Gặp vấn đề về lỗi của server',
                'error'
            )
        })
    }
    $scope.imageChanged = function (files) {
        var data = new FormData();
        data.append('file', files[0]);
        $http.post('/greens/rest/upload/images', data, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }

        }).then(resp => {
            $scope.form.photo = resp.data.name;

        }).catch(error => {
            alert("Loi");
            console.log("Error", error);
        })
    }

})

// app.controller("profile-ctrl", function ($scope, $http) {
//     $scope.form = {};
//     $scope.username = $("#username").val();
//     console.log("Username: ", $scope.username)
//     $scope.getAccount = function(){

//         $http.get(`/greens/rest/account/${$scope.username}`).then(resp => {
//             $scope.form = resp.data;
//         }).catch(error => {
//             console.error("Error Get: ", error)
//         })

//     }

//     $scope.getAccount();

//     $scope.update = function () {
//         $scope.put("", item).then(resp => {
//             console.log($scope.form);
//         }).catch(error => {
//             console.log("Error: ", error);
//             alert("Lỗi");
//         })
//     }

// });