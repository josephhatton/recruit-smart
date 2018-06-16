(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('CompanyCommentDeleteController', CompanyCommentDeleteController);

  CompanyCommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompanyComment'];

  function CompanyCommentDeleteController($uibModalInstance, entity, CompanyComment) {
    var vm = this;

    vm.companyComment = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      CompanyComment.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
