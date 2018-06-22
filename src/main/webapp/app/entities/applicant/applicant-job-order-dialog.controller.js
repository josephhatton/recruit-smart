(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantJobOrderDialogController', ApplicantJobOrderDialogController);

  ApplicantJobOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HiringContact', 'JobOrder', 'Applicant'];

  function ApplicantJobOrderDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, HiringContact, JobOrder, Applicant) {
    var vm = this;

    vm.applicant = entity;
    vm.clear = clear;
    vm.jobOrders = JobOrder.query();

    $timeout(function () {
    });

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function save() {
      vm.isSaving = true;
      if (vm.applicant.id !== null) {
        Applicant.update(vm.applicant, onSaveSuccess, onSaveError);
      } else {
        Applicant.save(vm.applicant, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:applicantUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
