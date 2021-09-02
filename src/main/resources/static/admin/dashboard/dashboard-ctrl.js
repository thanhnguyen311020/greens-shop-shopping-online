app.controller("dashboard-ctrl", function ($scope, $http) {

    var today = new Date();
    var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
    $scope.getDateStart = new Date(date);
    $scope.getDateEnd = new Date(date);
    //  $scope.count =0;
    $scope.getCountAccount = function () {

        $http.get("/greens/rest/account").then(resp => {
            //  $scope.accounts = resp.data;
            $scope.count = resp.data.length;
            console.log($scope.count)
        })

    }
    $scope.getCountAccount();

    // $scope.getCountAccount();

    // $scope.getcount = function(){

    //     return $scope.accounts.length;
    // }
    //   $scope.getcount();


    // Get Product
    $scope.getCountProduct = function () {
        $http.get("/greens/rest/product").then(resp => {
            $scope.countProduct = resp.data.length;
        })
    }
    $scope.getCountProduct();


    $scope.getCountOrder = function () {
        $http.get("/greens/rest/order").then(resp => {
            $scope.countOrder = resp.data.length;
        })
    }
    $scope.getCountOrder();

    $scope.getAmountOfMonth = function () {
        $http.get("/greens/rest/dashboard/amountOfMonth").then(resp => {
            $scope.amountMonth = resp.data;
            console.log(resp.data)
        })
    }
    $scope.getAmountOfMonth();

    //$scope.topPoduct =[];
    $scope.getTopProduct = function () {
        $http.get("http://localhost:8080/greens/rest/dashboard/topProduct").then(resp => {
            $scope.topPoduct = resp.data;
        })

    }
    $scope.getTopProduct();

    $scope.orderDetails =[];
    console.log("Date Now", date)
    $scope.getOrderDetailBetweenDate = function () {
        $scope.start = $("#start").val();
        $scope.end = $("#end").val();
        // console.log("Strat", $scope.start);
        // console.log("Type: ",typeof($scope.start))
        if ($scope.start == '' && $scope.end == '') {
            $scope.start = date;
            $scope.end = date;
        }
         console.log( "Date s-e",$scope.start + '- ' + $scope.end);
        $http.get(`/greens/rest/dashboard/${$scope.start}/${$scope.end}`).then(resp => {
            $scope.orderDetails = resp.data;
            console.log("Order Details", $scope.orderDetails);
        })
        $http.get(`/greens/rest/dashboard/sumAmount/${$scope.start}/${$scope.end}`).then(resp => {
            $scope.sumAmountByDate = resp.data;
            console.log("Sum Amount", $scope.sumAmountByDate);
        })
    }
    $scope.getOrderDetailBetweenDate();
})