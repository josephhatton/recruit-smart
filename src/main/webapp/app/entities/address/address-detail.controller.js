(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('AddressDetailController', AddressDetailController);

  AddressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Address'];

  function AddressDetailController($scope, $rootScope, $stateParams, previousState, entity, Address) {
    var vm = this;

    vm.address = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:addressUpdate', function (event, result) {
      vm.address = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();
