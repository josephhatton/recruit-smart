(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ActivityActionDialogController', ActivityActionDialogController);

  ActivityActionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ActivityAction'];

  function ActivityActionDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, ActivityAction) {
    var vm = this;

    vm.activityAction = entity;
    vm.clear = clear;
    vm.save = save;

    $timeout(function () {
      angular.element('.form-group:eq(1)>input').focus();
    });

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function save() {
      vm.isSaving = true;
      if (vm.activityAction.id !== null) {
        ActivityAction.update(vm.activityAction, onSaveSuccess, onSaveError);
      } else {
        ActivityAction.save(vm.activityAction, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:activityActionUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
