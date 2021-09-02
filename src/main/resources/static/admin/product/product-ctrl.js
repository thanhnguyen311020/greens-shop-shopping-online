app.controller("product-ctrl", function ($scope, $http) {
    $scope.items = [];
    $scope.cates = [];
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
        $scope.isShow=false;
    }

    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        $(".nav-tabs a:eq(0)").tab("show");
        $scope.isShow=true;
    }

    $scope.create = function () {
        var item = angular.copy($scope.form);
        $http.post("/greens/rest/product", item).then(resp => {
            resp.data.createDate = new Date(resp.data.createDate);
            $scope.items.push(resp.data);
            $scope.reset();
            Swal.fire(
                'Thêm mới thành công',
                '',
                'success'
            )
        }).catch(error => {
            Swal.fire(
                'Thêm mới thất bại',
                '',
                'error'
            )
            console.log("Error", error);
        })
    }

    $scope.selectUser = function (item){
        $scope.clickedItem=angular.copy(item);
        console.log($scope.clickedItem);
        $scope.message=item.name;
    }

    $scope.update = function () {
        var item = angular.copy($scope.form);
        $http.put(`/greens/rest/product/${item.id}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.id = item.id);
            $scope.items[index] = item;
      
            Swal.fire(
                'Cập nhập thành công',
                '',
                'success'
            )
            $scope.initialize();
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

        $http.delete(`/greens/rest/product/${item.id}`).then(resp => {
            var index = $scope.items.findIndex(p => p.id == item.id);
            $scope.items.splice(index, 1);
            $scope.reset();
            Swal.fire(
                'Xóa thành công',
                '',
                'success'
            )
            $scope.isShow=false;
        }).catch(error => {
            Swal.fire(
                'Xóa thất bại',
                '',
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
                'Lỗi tải hình ảnh',
                '',
                'error'
            )
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
    //                 $scope.pheading ="About Ús"
    //             }
    //         })
    //     }
    // }


    // Import File Excel
    $scope.import = function (files) {
        Swal.fire({
            title: 'Do you want to save the changes?',
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonText: `Save`,
            denyButtonText: `Don't save`,
        }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
                var reader = new FileReader();
                reader.onloadend = async () => { //=> reader.result

                    var workbook = new ExcelJS.Workbook();
                    await workbook.xlsx.load(reader.result);
                    const worksheet = workbook.getWorksheet('product');

                    worksheet.eachRow((row, index) => {
                        console.log("email", row.getCell(1).value);
                        if (index > 1) {
                            let product = {
                               
                                name: row.getCell(1).value,
                                price: +row.getCell(2).value,
                                quantity:+row.getCell(3).value,
                                createDate:new Date().getDate(row.getCell(4).value),
                                category: {id:row.getCell(5).value}
                            }
                            var url = "http://localhost:8080/greens/rest/product/importExcel";
                            $http.post(url, product).then(resp => {
                                console.log("Success", resp.data);
                                
                                Swal.fire('Saved!', '', 'success')
                                $scope.items.push(resp.data);
                                $('#fileEx').val('')
                            }).catch(error => {
                                console.log("Error", error);
                            })
                        }
                    });
                };
                reader.readAsArrayBuffer(files[0]);



            } else if (result.isDenied) {
                Swal.fire('Changes are not saved', '', 'info')
            }
        })



    }
})