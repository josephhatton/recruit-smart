(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactCommentDialogController', HiringContactCommentDialogController);

  HiringContactCommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HiringContactComment'];

  function HiringContactCommentDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, HiringContactComment) {
    var vm = this;

    vm.hiringContactComment = entity;
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
      if (vm.hiringContactComment.id !== null) {
        HiringContactComment.update(vm.hiringContactComment, onSaveSuccess, onSaveError);
      } else {
        HiringContactComment.save(vm.hiringContactComment, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:hiringContactCommentUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
