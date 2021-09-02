app.controller("authorityCtrl", function ($scope, $http) {
    $scope.roles = [];
    $scope.admins = [];
    $scope.authorities = [];

    $scope.initialize = function () {

        //load all roles
        $http.get("/greens/rest/roles").then(resp => {
            $scope.roles = resp.data;
            console.log("ROLES: ",resp.data);
        });


        // load staff and driector (administrator)
        $http.get("/greens/rest/account/role?admin=true").then(resp => {
           $scope.admins = resp.data;
            console.log("Account administrator: ",resp.data);
        });

        // load authorities of staffs and directors
        $http.get("/greens/rest/authorities/ok?admin=true").then(resp => {
            $scope.authorities = resp.data;
            console.log("Authority: ",resp.data);
        })
    }

    $scope.authority_of = function (acc, role) {
        if ($scope.authorities) {
            return $scope.authorities.find(ur => ur.account.username = acc.username && ur.role.id == role.id);
        }
    }

    $scope.authority_changed = function (acc, role) {
        var authority = $scope.authority_of(acc, role);
        if (authority) {// da cap quyen ==> thu hoi quyen (xoa)
            $scope.revoke_authority(authority);
        } else {// chua cap quyen => cap quyen (them moi);
            authority = { account: acc, role: role };
            $scope.grant_authority(authority);
        }
    }

    $scope.grant_authority = function (authority) {
        $http.post("/greens/rest/authorities", authority).then(resp => {
            $scope.authorities.push(resp.data);
            alert("Cap quyen su dung thanh cong");
        }).catch(error => {
            alert("Cap quyen that bai")
            console.log("Error: ", error);
        })
    }

    // Xoa authority
    $scope.revoke_authority = function (authority) {
        $http.delete(`/greens/rest/authorities/${authority.id}`).then(resp => {
            var index = $scope.authorities.findIndex(a => a.id == authority.id);
            $scope.authorities.splice(index, 1);
            alert("thu hoi quyen thanh cong")
        }).catch(error => {
            console.log("Error", error);
            alert("Thu hoi quyen that bai")
        })
    }


    $scope.initialize();
})