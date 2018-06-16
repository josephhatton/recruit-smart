(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('WorkStatusDialogController', WorkStatusDialogController);

  WorkStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkStatus'];

  function WorkStatusDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, WorkStatus) {
    var vm = this;

    vm.workStatus = entity;
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
      if (vm.workStatus.id !== null) {
        WorkStatus.update(vm.workStatus, onSaveSuccess, onSaveError);
      } else {
        WorkStatus.save(vm.workStatus, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:workStatusUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
