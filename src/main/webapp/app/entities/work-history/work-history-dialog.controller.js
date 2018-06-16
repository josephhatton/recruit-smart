(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('WorkHistoryDialogController', WorkHistoryDialogController);

  WorkHistoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkHistory'];

  function WorkHistoryDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, WorkHistory) {
    var vm = this;

    vm.workHistory = entity;
    vm.clear = clear;
    vm.datePickerOpenStatus = {};
    vm.openCalendar = openCalendar;
    vm.save = save;

    $timeout(function () {
      angular.element('.form-group:eq(1)>input').focus();
    });

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function save() {
      vm.isSaving = true;
      if (vm.workHistory.id !== null) {
        WorkHistory.update(vm.workHistory, onSaveSuccess, onSaveError);
      } else {
        WorkHistory.save(vm.workHistory, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:workHistoryUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }

    vm.datePickerOpenStatus.startDate = false;
    vm.datePickerOpenStatus.endDate = false;

    function openCalendar(date) {
      vm.datePickerOpenStatus[date] = true;
    }
  }
})();
