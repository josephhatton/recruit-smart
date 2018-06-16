(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantStatusDialogController', ApplicantStatusDialogController);

  ApplicantStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ApplicantStatus'];

  function ApplicantStatusDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, ApplicantStatus) {
    var vm = this;

    vm.applicantStatus = entity;
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
      if (vm.applicantStatus.id !== null) {
        ApplicantStatus.update(vm.applicantStatus, onSaveSuccess, onSaveError);
      } else {
        ApplicantStatus.save(vm.applicantStatus, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:applicantStatusUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
