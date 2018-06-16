(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderCommentDeleteController', JobOrderCommentDeleteController);

  JobOrderCommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'JobOrderComment'];

  function JobOrderCommentDeleteController($uibModalInstance, entity, JobOrderComment) {
    var vm = this;

    vm.jobOrderComment = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      JobOrderComment.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
