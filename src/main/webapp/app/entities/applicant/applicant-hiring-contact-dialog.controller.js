(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantHiringContactDialogController', ApplicantHiringContactDialogController);

  ApplicantHiringContactDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HiringContact', 'Applicant'];

  function ApplicantHiringContactDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, HiringContact, Applicant) {
    var vm = this;

    vm.applicant = entity;
    vm.clear = clear;
    vm.hiringContacts = HiringContact.query();

    $timeout(function () {
    });

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    vm.changedHiringContact = function (checked,job) {
      if(checked) {
        vm.applicant.hiringContacts.push(job)
      } else {
        for (var i = 0; i < vm.applicant.hiringContacts.length; i++) {
          if (vm.applicant.hiringContacts[i].id === job.id) {
            vm.applicant.hiringContacts.splice(i, 1);
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
