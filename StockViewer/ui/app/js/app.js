var app = angular.module('myApp', []);
app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.timeout = 20000;
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.headers.common = 'Content-Type: application/json';
    delete $httpProvider.defaults.headers.common['X-Requested-With']
}])
app.controller('myCtrl', function($scope, $http) {
    this.retrieve = function() {
    $http.get('http://127.0.0.1:8761/api/stock-service/rest/stock/' + $scope.name)
    .then(function (response) {
        response.header("Content-Type","application/json");
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Headers", "X-Requested-With");
        console.log('inside'+ response);
        $scope.quotes = response.data;
    }, function (response) {
        console.log('came here');
    });
    }


    this.add = function() {
        var message = {
            userName: $scope.name,
            quotes: [$scope.quote]
        }
        $http.post('http://127.0.0.1:8761/api/db-service/rest/db/add', message)
            .then(function(response) {
                $scope.quotes = response.data;
            }, function(response) {
                console.log('error..');
            });
    }
});