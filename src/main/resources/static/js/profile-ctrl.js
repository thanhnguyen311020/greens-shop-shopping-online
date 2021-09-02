var app = angular.module("profile", []);

app.controller("profile-ctrl", function ($scope, $http) {
    $scope.form = {};
    $scope.username = $("#username").val();
    console.log("Username: ", $scope.username)
    $scope.getAccount = function(){

        $http.get(`/greens/rest/account/${$scope.username}`).then(resp => {
            $scope.form = resp.data;
        }).catch(error => {
            console.error("Error Get: ", error)
        })

    }

    $scope.getAccount();

    $scope.update = function () {
        var item = angular.copy($scope.form);
        $scope.put(`/greens/rest/account/${item.username}`, item).then(resp => {
            console.log($scope.form);
            alert("Cập Nhập Thành Công")
        }).catch(error => {
            console.log("Error: ", error);
            alert("Lỗi");
        })
    }

});