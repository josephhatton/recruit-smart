(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('SearchListDeleteController', SearchListDeleteController);

  SearchListDeleteController.$inject = ['$uibModalInstance', 'entity', 'SearchList'];

  function SearchListDeleteController($uibModalInstance, entity, SearchList) {
    var vm = this;
    vm.searchList = entity;
    vm.clear = function () {
      $uibModalInstance.dismiss('cancel');
    };
    vm.confirmDelete = function (id) {
      SearchList.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    };
  }
})();
