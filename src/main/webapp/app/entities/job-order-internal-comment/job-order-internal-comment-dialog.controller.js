(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderInternalCommentDialogController', JobOrderInternalCommentDialogController);

  JobOrderInternalCommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'JobOrderInternalComment'];

  function JobOrderInternalCommentDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, JobOrderInternalComment) {
    var vm = this;

    vm.jobOrderInternalComment = entity;
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
      if (vm.jobOrderInternalComment.id !== null) {
        JobOrderInternalComment.update(vm.jobOrderInternalComment, onSaveSuccess, onSaveError);
      } else {
        JobOrderInternalComment.save(vm.jobOrderInternalComment, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:jobOrderInternalCommentUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
