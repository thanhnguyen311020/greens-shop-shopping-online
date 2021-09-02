app.controller("detail-ctrl", function ($scope, $http, $routeParams) {
    $scope.items = [];
    $scope.form = {};
    $scope.id = $routeParams.id;

    $scope.initialize = function () {

        $http.get(`/greens/rest/orderDetail/${$routeParams.id}`).then(resp => {
            $scope.items = resp.data;
            console.log($scope.items)

            console.log(resp.data[0].order.id)
        });

    }
    $scope.initialize();

    // $scope.loadPrice = function(id){
    //     $http.get(`/greens/rest/product/price/${id}`).then(resp =>{
    //         $scope.form.price=resp.data;
    //     }).catch(error =>{
    //         console.log(error)
    //     })
    // }

    //reset form
    $scope.reset = function () {
        $scope.form = {

        };
        $scope.initialize();

    }

    $scope.edit = function (item) {

        $scope.form = angular.copy(item);
        $(".nav-tabs a:eq(0)").tab("show");
    }

    $scope.create = function () {
        var item = angular.copy($scope.form);

        console.log("Item= ", item)
        $http.post("/greens/rest/orderDetail", item).then(resp => {
            $scope.items.push(resp.data);
            $scope.reset();
            Swal.fire(
                'Thêm thành công',
                '',
                'success'
            )
        }).catch(error => {
            Swal.fire(
                'Thêm thất bại',
                '',
                'error'
            )
            console.log("Error", error);
        })
    }

    $scope.update = function () {
        var item = angular.copy($scope.form);
        $http.put(`/greens/rest/orderDetail/${item.id}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.id = item.id);
            $scope.items[index] = item;
            $scope.items;
            Swal.fire(
                'Cập nhập thành công',
                '',
                'success'
            )
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

        $http.delete(`/greens/rest/orderDetail/${item.id}`).then(resp => {
            var index = $scope.items.findIndex(p => p.id == item.id);
            $scope.items.splice(index, 1);
            $scope.reset();
            Swal.fire(
                'Xóa thành công',
                '',
                'success'
            )
        }).catch(error => {
            Swal.fire(
                'Xóa thất bại',
                '',
                'error'
            )
            console.log("Error", error);
        })
    }

    // $scope.imageChanged = function (files) {
    //     var data = new FormData();
    //     data.append('file', files[0]);
    //     $http.post('/greens/rest/upload/images', data, {
    //         transformRequest: angular.identity,
    //         headers: { 'Content-Type': undefined }

    //     }).then(resp => {
    //         $scope.form.image = resp.data.name;
    //         alert(resp.data.name)
    //         alert(resp.data)
    //     }).catch(error => {
    //         alert("Loi");
    //         console.log("Error", error);
    //     })
    // }
    // $scope.initialize();

    // $scope.pager = {
    //     page: 0,
    //     size: 10,
    //     get items() {
    //         var start = this.page * this.size;
    //         return $scope.items.slice(start, start + this.size);
    //     },
    //     get count(){// tong so san pham chia cho kich thuong moi trang va lam tron
    //         return Math.ceil(1.0 *$scope.items.length/this.size);
    //     },
    //     first(){
    //         this.page=0;
    //     },
    //     prev(){
    //         this.page--;
    //         if(this.page<0){
    //             this.last();
    //         }
    //     },
    //     next(){
    //         this.page++;
    //         if(this.page >=this.count){
    //             this.first();
    //         }
    //     },
    //     last(){
    //         this.page = this.count-1;
    //     }

    // }

})