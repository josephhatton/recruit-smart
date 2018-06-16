(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderCommentDialogController', JobOrderCommentDialogController);

  JobOrderCommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'JobOrderComment'];

  function JobOrderCommentDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, JobOrderComment) {
    var vm = this;

    vm.jobOrderComment = entity;
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
      if (vm.jobOrderComment.id !== null) {
        JobOrderComment.update(vm.jobOrderComment, onSaveSuccess, onSaveError);
      } else {
        JobOrderComment.save(vm.jobOrderComment, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:jobOrderCommentUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
