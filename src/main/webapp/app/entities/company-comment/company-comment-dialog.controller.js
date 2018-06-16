(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('CompanyCommentDialogController', CompanyCommentDialogController);

  CompanyCommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CompanyComment'];

  function CompanyCommentDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, CompanyComment) {
    var vm = this;

    vm.companyComment = entity;
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
      if (vm.companyComment.id !== null) {
        CompanyComment.update(vm.companyComment, onSaveSuccess, onSaveError);
      } else {
        CompanyComment.save(vm.companyComment, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:companyCommentUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
