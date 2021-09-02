app.controller("account-ctrl", function ($scope, $http) {
    $scope.items = [];
    $scope.cates = [];
    $scope.form = {};

    $scope.isShow = false;

    // dirPaginate 
    $scope.pageSize = 10;
    $scope.sort = function (keyname) {
        $scope.sortKey = keyname; // set the sortKey to the param passed
        $scope.reverse = !$scope.reverse // if true make it fasle and vice versa
    }
    //

    $scope.form.createDate = new Date();

    $scope.initialize = function () {
        $http.get("/greens/rest/account").then(resp => {
            $scope.items = resp.data;
            //console.log(resp.data);
            $scope.items.forEach(item => {
                item.createDate = new Date(item.createDate)
            })
        });
        //load product
        //load categories
        // $http.get("/greens/rest/categories").then(resp => {
        //     $scope.cates = resp.data;
        // })
    }
    $scope.initialize();


    // reset form
    $scope.reset = function () {
        $scope.form = {
            createDate: new Date(),
            photo: 'noImage.jpg',

        };
        document.getElementById("password").value = "";
        $scope.isShow = false;
    }

    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        $(".nav-tabs a:eq(0)").tab("show");
        $scope.isShow = true;
    }

    $scope.selectUser = function (item) {
        $scope.clickedItem = angular.copy(item);
        console.log($scope.clickedItem);
        $scope.message = item.fullname;
    }

    $scope.create = function () {
        //$scope.form.password= $("#password").val();
        var item = angular.copy($scope.form);
        item.password = $('#password').val();
        $http.post("/greens/rest/account", item).then(resp => {
            resp.data.createDate = new Date(resp.data.createDate);
            $scope.items.push(resp.data);
            $scope.reset();
            Swal.fire(
                'Thêm mới thành công',
                '',
                'success'
            )
            $scope.isShow = false;
        }).catch(error => {
            Swal.fire(
                'Thêm mới thất bại',
                '',
                'error'
            )
            console.log("Error", error);
        })
    }

    $scope.update = function () {

        var item = angular.copy($scope.form);
        item.password = $('#password').val();
        $http.put(`/greens/rest/account/${item.username}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.id = item.id);
            $scope.items[index] = item;
            $scope.items;
            Swal.fire(
                'Cập nhập thành công',
                '',
                'success'
            )
            $('#password').val('');
            $scope.isShow = true;
        }).catch(error => {
            Swal.fire(
                'Cập nhập thất bại',
                '',
                'error'
            )
            console.log("Error", error);
        })
    }

    $scope.delete = function (item) {

        $http.delete(`/greens/rest/account/${item.username}`).then(resp => {
            var index = $scope.items.findIndex(p => p.id == item.id);
            $scope.items.splice(index, 1);
            $scope.reset();
            Swal.fire(
                'Xóa thành công',
                '',
                'success'
            )
            $scope.isShow = false;
        }).catch(error => {
            Swal.fire(
                'Xóa thất bại',
                '',
                'success'
            )
            console.log("Error", error);
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
            Swal.fire(
                'Lỗi hình ảnh',
                '',
                'error'
            )
            console.log("Error", error);
        })
    }
    // $scope.initialize();

    $scope.pager = {
        page: 0,
        size: 10,
        get items() {
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },
        get count() {// tong so san pham chia cho kich thuong moi trang va lam tron
            return Math.ceil(1.0 * $scope.items.length / this.size);
        },
        first() {
            this.page = 0;
        },
        prev() {
            this.page--;
            if (this.page < 0) {
                this.last();
            }
        },
        next() {
            this.page++;
            if (this.page >= this.count) {
                this.first();
            }
        },
        last() {
            this.page = this.count - 1;
        }

    }
})