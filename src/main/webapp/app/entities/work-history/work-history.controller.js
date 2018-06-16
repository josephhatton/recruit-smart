(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('WorkHistoryController', WorkHistoryController);

  WorkHistoryController.$inject = ['WorkHistory', 'WorkHistorySearch'];

  function WorkHistoryController(WorkHistory, WorkHistorySearch) {

    var vm = this;

    vm.workHistories = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      WorkHistory.query(function (result) {
        vm.workHistories = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      WorkHistorySearch.query({query: vm.searchQuery}, function (result) {
        vm.workHistories = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
