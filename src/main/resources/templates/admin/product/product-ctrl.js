app.controller("product-ctrl", function ($scope, $http) {
    $scope.items = [];
    $scope.cates = [];
    $scope.form = {};

    $scope.initialize = function () {
        $http.get("/greens/rest/product").then(resp => {
            $scope.items = resp.data;
            $scope.items.forEach(item => {
                item.createDate = new Date(item.createDate)
            })
        });
        //load product
        //load categories
        $http.get("/greens/rest/categories").then(resp => {
            $scope.cates = resp.data;
        })
    }



    // reset form
    $scope.reset = function () {
        $scope.form = {
            createDate: new Date(),
            image: 'buy-1.jpg',
            available: true
        };

    }

    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        $(".nav-tabs a:eq(0)").tab("show");
    }

    $scope.create = function () {
        var item = angular.copy($scope.form);
        $http.post("/greens/rest/product", item).then(resp => {
            resp.data.createDate = new Date(resp.data.createDate);
            $scope.items.push(resp.data);
            $scope.reset();
            alert("Them thanh cong");
        }).catch(error => {
            alert("Loix Them moi san pham");
            console.log("Error", error);
        })
    }

    $scope.update = function () {
        var item = angular.copy($scope.form);
        $http.put(`/greens/rest/product/${item.id}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.id = item.id);
            $scope.items[index] = item;
            $scope.items;
            alert("ok");
        }).catch(error => {
            alert("Loi")
            console.log("Error", error);
        })
    }

    $scope.delete = function (item) {

        $http.delete(`/greens/rest/product/${item.id}`).then(resp => {
            var index = $scope.items.findIndex(p => p.id == item.id);
            $scope.items.splice(index, 1);
            $scope.reset();
            alert("xoa thanh cong")
        }).catch(error => {
            alert("Error")
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
            $scope.form.image = resp.data.name;
            alert(resp.data.name)
            alert(resp.data)
        }).catch(error => {
            alert("Loi");
            console.log("Error", error);
        })
    }
    $scope.initialize();

    $scope.pager = {
        page: 0,
        size: 10,
        get items() {
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },
        get count(){// tong so san pham chia cho kich thuong moi trang va lam tron
            return Math.ceil(1.0 *$scope.items.length/this.size);
        },
        first(){
            this.page=0;
        },
        prev(){
            this.page--;
            if(this.page<0){
                this.last();
            }
        },
        next(){
            this.page++;
            if(this.page >=this.count){
                this.first();
            }
        },
        last(){
            this.page = this.count-1;
        }

    }

    // $scope.modal_test = function($scope, $modal, $rootScope){
    //     $scope.open = function(){
    //         $modal.open({
    //             templateUrl: 'admin/modal.html',
    //             backdrop : true,
    //             windowClass: 'modal',
    //             controller : function($scope, $route){
    //                 $scope.pheading ="About ??s"
    //             }
    //         })
    //     }
    // }
})