(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactInternalCommentDialogController', HiringContactInternalCommentDialogController);

  HiringContactInternalCommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HiringContactInternalComment'];

  function HiringContactInternalCommentDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, HiringContactInternalComment) {
    var vm = this;

    vm.hiringContactInternalComment = entity;
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
      if (vm.hiringContactInternalComment.id !== null) {
        HiringContactInternalComment.update(vm.hiringContactInternalComment, onSaveSuccess, onSaveError);
      } else {
        HiringContactInternalComment.save(vm.hiringContactInternalComment, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:hiringContactInternalCommentUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
