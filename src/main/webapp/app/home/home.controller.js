(function() {
    'use strict';

    angular
        .module('recruitsmartApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'HotListBucket'];

    function HomeController ($scope, Principal, LoginService, $state, HotListBucket) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            load();
        });

        load();

        function load() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });

            HotListBucket.query(function(result) {
                vm.bucket = result;
            });

        }
        function register () {
            $state.go('register');
        }
    }
})();
