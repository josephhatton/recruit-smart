(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantInternalCommentDialogController', ApplicantInternalCommentDialogController);

  ApplicantInternalCommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ApplicantInternalComment'];

  function ApplicantInternalCommentDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, ApplicantInternalComment) {
    var vm = this;

    vm.applicantInternalComment = entity;
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
      if (vm.applicantInternalComment.id !== null) {
        ApplicantInternalComment.update(vm.applicantInternalComment, onSaveSuccess, onSaveError);
      } else {
        ApplicantInternalComment.save(vm.applicantInternalComment, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:applicantInternalCommentUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
