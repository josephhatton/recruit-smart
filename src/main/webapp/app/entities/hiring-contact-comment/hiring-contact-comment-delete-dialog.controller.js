(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactCommentDeleteController', HiringContactCommentDeleteController);

  HiringContactCommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'HiringContactComment'];

  function HiringContactCommentDeleteController($uibModalInstance, entity, HiringContactComment) {
    var vm = this;

    vm.hiringContactComment = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      HiringContactComment.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
