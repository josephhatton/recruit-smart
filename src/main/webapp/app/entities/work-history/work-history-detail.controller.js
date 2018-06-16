(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('WorkHistoryDetailController', WorkHistoryDetailController);

  WorkHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkHistory'];

  function WorkHistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkHistory) {
    var vm = this;

    vm.workHistory = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:workHistoryUpdate', function (event, result) {
      vm.workHistory = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();
