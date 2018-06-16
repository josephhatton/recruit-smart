(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderInternalCommentDeleteController', JobOrderInternalCommentDeleteController);

  JobOrderInternalCommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'JobOrderInternalComment'];

  function JobOrderInternalCommentDeleteController($uibModalInstance, entity, JobOrderInternalComment) {
    var vm = this;

    vm.jobOrderInternalComment = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      JobOrderInternalComment.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
