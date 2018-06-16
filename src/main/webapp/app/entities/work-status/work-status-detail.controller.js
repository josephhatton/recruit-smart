(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('WorkStatusDetailController', WorkStatusDetailController);

  WorkStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkStatus'];

  function WorkStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkStatus) {
    var vm = this;

    vm.workStatus = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:workStatusUpdate', function (event, result) {
      vm.workStatus = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();
