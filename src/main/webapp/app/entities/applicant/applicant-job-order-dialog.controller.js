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

    vm.changedJobOrder = function (checked,job) {
      if(checked) {
        vm.applicant.jobOrders.push(job)
      } else {
        for (var i = 0; i < vm.applicant.jobOrders.length; i++) {
          if (vm.applicant.jobOrders[i].id === job.id) {
            vm.applicant.jobOrders.splice(i, 1);
            return
          }
        }
      }
    }

    vm.save = function () {
        Applicant.update(vm.applicant, onSaveSuccess, onSaveError);
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:applicantUpdate', result);
      vm.applicant = result
      $uibModalInstance.close(result);
    }

    function onSaveError() {
    }


  }
})();
