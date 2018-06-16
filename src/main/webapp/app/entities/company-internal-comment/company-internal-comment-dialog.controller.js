(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('CompanyInternalCommentDialogController', CompanyInternalCommentDialogController);

  CompanyInternalCommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CompanyInternalComment'];

  function CompanyInternalCommentDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, CompanyInternalComment) {
    var vm = this;

    vm.companyInternalComment = entity;
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
      if (vm.companyInternalComment.id !== null) {
        CompanyInternalComment.update(vm.companyInternalComment, onSaveSuccess, onSaveError);
      } else {
        CompanyInternalComment.save(vm.companyInternalComment, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:companyInternalCommentUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
