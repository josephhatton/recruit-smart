(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantDialogController', ApplicantDialogController);

  ApplicantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Applicant', 'Address', 'ApplicantStatus', 'ApplicantComment', 'Skill', 'WorkHistory', 'ApplicantInternalComment', 'WorkStatus', 'Activity'];

  function ApplicantDialogController($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Applicant, Address, ApplicantStatus, ApplicantComment, Skill, WorkHistory, ApplicantInternalComment, WorkStatus, Activity) {
    var vm = this;

    vm.applicant = entity;
    vm.clear = clear;
    vm.byteSize = DataUtils.byteSize;
    vm.openFile = DataUtils.openFile;
    vm.save = save;
    vm.addresses = Address.query();
    vm.applicantstatuses = ApplicantStatus.query();
    vm.applicantcomments = ApplicantComment.query();
    vm.skills = Skill.query();
    vm.workhistories = WorkHistory.query();
    vm.applicantinternalcomments = ApplicantInternalComment.query();
    vm.workstatuses = WorkStatus.query();
    vm.activities = Activity.query();

    $timeout(function () {
      angular.element('.form-group:eq(1)>input').focus();
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
