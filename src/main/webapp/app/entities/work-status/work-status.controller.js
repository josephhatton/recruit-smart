(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('WorkStatusController', WorkStatusController);

  WorkStatusController.$inject = ['WorkStatus', 'WorkStatusSearch'];

  function WorkStatusController(WorkStatus, WorkStatusSearch) {

    var vm = this;

    vm.workStatuses = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      WorkStatus.query(function (result) {
        vm.workStatuses = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      WorkStatusSearch.query({query: vm.searchQuery}, function (result) {
        vm.workStatuses = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
