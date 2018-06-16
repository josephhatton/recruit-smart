(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ActivityActionDetailController', ActivityActionDetailController);

  ActivityActionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ActivityAction'];

  function ActivityActionDetailController($scope, $rootScope, $stateParams, previousState, entity, ActivityAction) {
    var vm = this;

    vm.activityAction = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:activityActionUpdate', function (event, result) {
      vm.activityAction = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();
