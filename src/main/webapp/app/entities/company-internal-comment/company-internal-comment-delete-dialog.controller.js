(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('CompanyInternalCommentDeleteController', CompanyInternalCommentDeleteController);

  CompanyInternalCommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompanyInternalComment'];

  function CompanyInternalCommentDeleteController($uibModalInstance, entity, CompanyInternalComment) {
    var vm = this;

    vm.companyInternalComment = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      CompanyInternalComment.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
