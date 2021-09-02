var app = angular.module("admin-app", [ 'ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider
        .when("/dashboard", {
            templateUrl: "/admin/dashboard/dashboard.html",
            controller: 'dashboard-ctrl'

        })
        .when("/order", {
            templateUrl: "/admin/order/index.html",
            controller: "order-ctrl"
        })
        .when('/detail/:id', {
            templateUrl: "/admin/detail/index.html",
            controller: "detail-ctrl"
        })
        .when("/product", {
            templateUrl: "/admin/product/index.html",
            controller: "product-ctrl"
        })
        .when("/categories", {
            templateUrl: "/admin/category/index.html",
            controller: "categories-ctrl"
        })
        .when("/authority", {
            templateUrl: "/admin/authority/index.html",
            controller: "authorityCtrl"
        })
        .when("/account", {
            templateUrl: "/admin/account/index.html",
            controller: "account-ctrl"
        })
      
        

        .otherwise({
            templateUrl: "/admin/dashboard/dashboard.html",
            controller: 'dashboard-ctrl'
        });

        //ok
})