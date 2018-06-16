(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactInternalCommentDeleteController', HiringContactInternalCommentDeleteController);

  HiringContactInternalCommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'HiringContactInternalComment'];

  function HiringContactInternalCommentDeleteController($uibModalInstance, entity, HiringContactInternalComment) {
    var vm = this;

    vm.hiringContactInternalComment = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      HiringContactInternalComment.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
