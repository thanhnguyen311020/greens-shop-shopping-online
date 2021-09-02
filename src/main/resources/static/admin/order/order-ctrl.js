app.controller("order-ctrl", function ($scope, $http) {
    $scope.items = [];
   
    $scope.form = {};

    $scope.itemDetail =[];

        // dirPaginate 
        $scope.pageSize=10;
        $scope.sort = function(keyname){
            $scope.sortKey = keyname; // set the sortKey to the param passed
            $scope.reverse = !$scope.reverse // if true make it fasle and vice versa
        }
        //

    $scope.initialize = function () {
       
        $http.get("/greens/rest/order").then(resp => {
            $scope.items = resp.data;
            console.log("Order: ",$scope.items)
            $scope.items.forEach(item => {
                item.createDate = new Date(item.createDate)
            })
        });
   
    }


    $scope.initialize();
    //reset form
    $scope.reset = function () {
        $scope.form = {
            
            image: 'buy-1.jpg',
          
        };

    }


    // reset form
    // $scope.reset = function () {
    //     $scope.form = {
    //         createDate: new Date(),
    //         image: 'buy-1.jpg',
    //         available: true
    //     };

    // }

    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        $(".nav-tabs a:eq(0)").tab("show");
    }
    $scope.edit1 = function (item) {
        $scope.form = angular.copy(item);

        $http.get(`/greens/rest/orderDetail/${item.id}`).then(resp => {
            $scope.details=resp.data;
            console.log("Details: ",resp.data);
        })
       // $(".nav-tabs a:eq(0)").tab("show");
    }

    $scope.updateStatusOrder = function(){
        var item = angular.copy($scope.form);
        $http.put(`/greens/rest/order/updateStatus/${item.id}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.id = item.id);
            $scope.items[index] = item;
            $scope.items;
            Swal.fire(
                'Cập nhập trạng thái thành công',
                '',
                'success'
            )
            $('#editModal').modal('toggle');
            $scope.initialize();
        }).catch(error => {
            Swal.fire(
                'Cập nhập trạng thái thất bại',
                '',
                'error'
            )
            console.log("Error", error);
        })
    }

    // $scope.create = function () {
    //     var item = angular.copy($scope.form);
    //     $http.post("/greens/rest/product", item).then(resp => {
    //         resp.data.createDate = new Date(resp.data.createDate);
    //         $scope.items.push(resp.data);
    //         $scope.reset();
    //         alert("Them thanh cong");
    //     }).catch(error => {
    //         alert("Loix Them moi san pham");
    //         console.log("Error", error);
    //     })
    // }

    $scope.selectUser = function (item){
        $scope.clickedItem=angular.copy(item);
        console.log($scope.clickedItem);
        $scope.message=item.id;
    }

    $scope.update = function () {
        var item = angular.copy($scope.form);
        $http.put(`/greens/rest/order/${item.id}`, item).then(resp => {
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

        $http.delete(`/greens/rest/order/${item.id}`).then(resp => {
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
    };

  
    $scope.details = function(id){

         
        $http.get(`/greens/rest/orderDetail/${id}`).then(resp => {
            $scope.itemDetail = resp.data;
            console.log($scope.itemDetail)
            location.href ="#!detail/"
        });
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

    //$scope.products =list;

    // $scope.pager={
    //     begin =0,
    //     pageSize=10,

    //     pageCount = Math.ceil($scope.items.length/this.pageSize),

    //     repaginate(){
    //         begin=0;
    //         pageCount =Math.ceil($scope.items.length/ $scope.pageSize);
    //     },
    //     first(){
    //         this.begin=0
    //     },
    //     prev(){
    //         this.begin--;
    //         if(this.begin>0){
    //             this.begin = this.pageSize;
    //         }
    //     },
    //     next(){
    //         this.begin++;
    //         if(this.begin>= (this.pageCount -1)*this.pageSize ){
    //             this.begin=0;
    //         }
    //     },
    //     last(){
    //         this.begin = (this.pageCount -1)*this.pageSize ;
    //     }


        
        
    // }

    // $scope.begin=0;
    // $scope.pageSize =8;

    // $scope.pageCount =Math.ceil($scope.products.length/$scope.pageSize);

    // $scope.prop ='price';

    // $scope.repaginate = function(){
    //     $scope.begin =0;
    //     $scope.pageCount =  Math.ceil($scope.products.length/ $scope.pageSize);
    // }

    // $scope.sortBy = function(prop){
    //     $scope.prop =prop;
    // }

    // $scope.first = function(){
    //     $scope.begin =0;
    // }

    // $scope.prev = function(){
    //     if($scope.begin>0){
    //         $scope.begin-=$scope.pageSize;
    //     }
    // }

    // $scope.next = function(){
    //     if($scope.begin<($scope.pageCount -1) * $scope.pageSize){
    //         $scope.begin +=$scope.pageSize;
    //     }
    // }

    // $scope.last = function(){
    //     $scope.begin = ($scope.pageCount-1)* $scope.pageSize
    // }
})