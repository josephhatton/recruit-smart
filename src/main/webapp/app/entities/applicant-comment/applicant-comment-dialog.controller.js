(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantCommentDialogController', ApplicantCommentDialogController);

  ApplicantCommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ApplicantComment'];

  function ApplicantCommentDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, ApplicantComment) {
    var vm = this;

    vm.applicantComment = entity;
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
      if (vm.applicantComment.id !== null) {
        ApplicantComment.update(vm.applicantComment, onSaveSuccess, onSaveError);
      } else {
        ApplicantComment.save(vm.applicantComment, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:applicantCommentUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
