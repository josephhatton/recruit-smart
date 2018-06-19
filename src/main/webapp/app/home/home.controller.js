(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HomeController', HomeController);

  HomeController.$inject = ['$scope', 'Principal', 'LoginService','filterFilter', '$state', 'HotListBucket', 'HotList'];

  function HomeController($scope, Principal, LoginService, filterFilter, $state, HotListBucket, HotList) {
    var vm = this;

    vm.account = null;
    vm.isAuthenticated = null;
    vm.login = LoginService.open;
    vm.register = register;
    $scope.$on('authenticationSuccess', function () {
      load();
    });

    load();

    function load() {
      Principal.identity().then(function (account) {
        vm.account = account;
        vm.isAuthenticated = Principal.isAuthenticated;
      });

      HotListBucket.query(function (result) {
        vm.bucket = result;
      });
      HotList.query(function (result) {
        vm.hotLists = result;
        vm.applicants = filterFilter(vm.hotLists, {hotListType: 'Applicants'});
        vm.jobOrders = filterFilter(vm.hotLists, {hotListType: 'Job Orders'});
      });
    }

    function register() {
      $state.go('register');
    }
  }
})();
