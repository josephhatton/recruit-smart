(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('SearchListDetailController', SearchListDetailController);

  SearchListDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SearchList', 'User'];

  function SearchListDetailController($scope, $rootScope, $stateParams, entity, SearchList, User) {
    var vm = this;
    vm.searchList = entity;
    vm.load = function (id) {
      SearchList.get({id: id}, function (result) {
        vm.searchList = result;
      });
    };
    var unsubscribe = $rootScope.$on('recruitSmartApp:searchListUpdate', function (event, result) {
      vm.searchList = result;
    });
    $scope.$on('$destroy', unsubscribe);

  }
})();
