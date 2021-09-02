app.controller("categories-ctrl", function ($scope, $http) {
    $scope.items = [];
   
    $scope.form = {};
    
    $scope.isShow=false;

         // dirPaginate 
         $scope.pageSize=10;
         $scope.sort = function(keyname){
             $scope.sortKey = keyname; // set the sortKey to the param passed
             $scope.reverse = !$scope.reverse // if true make it fasle and vice versa
         }
         //

    $scope.initialize = function () {
       
        $http.get("/greens/rest/categories").then(resp => {
            $scope.items = resp.data;
            console.log($scope.items)
           
        });
   
    }


    $scope.initialize();
    //reset form
    $scope.reset = function () {
        $scope.form = {
            
            image: 'noImage.jpg',
          
        };
        $scope.isShow=false;
    }

    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        $(".nav-tabs a:eq(0)").tab("show");
        $scope.isShow=true;
    }

    $scope.selectUser = function (item){
        $scope.clickedItem=angular.copy(item);
        console.log($scope.clickedItem);
        $scope.message=item.name;
    }

    $scope.create = function () {
        var item = angular.copy($scope.form);
        $http.post("/greens/rest/categories", item).then(resp => {
            $scope.items.push(resp.data);
            $scope.reset();
             Swal.fire(
               
                'Thêm thành công',
                '',
                'success'
            )
            $scope.isShow=false;
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
        $http.put(`/greens/rest/categories/${item.id}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.id = item.id);
            $scope.items[index] = item;
            $scope.items;
            Swal.fire(
                'Cập nhập thành công',
                '',
                'success'
            )
            $scope.isShow=true;
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

        $http.delete(`/greens/rest/categories/${item.id}`).then(resp => {
            var index = $scope.items.findIndex(p => p.id == item.id);
            $scope.items.splice(index, 1);
            $scope.reset();
            // alert("xoa thanh cong");
            Swal.fire(
                'Thông báo',
                'Xóa thành công',
                'success'
            )
            $scope.isShow=false;
        }).catch(error => {
            Swal.fire(
                'Thông báo',
                'Xóa thành công',
                'error'
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
            $scope.form.image = resp.data.name;
            
        }).catch(error => {
            Swal.fire(
                'Lỗi thêm hình ảnh',
                '',
                'error'
            )
            console.log("Error", error);
        })
    }
    // $scope.initialize();

    $scope.pager = {
        page: 0,
        size: 5,
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




    
})
