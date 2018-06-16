(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HotListController', HotListController);

  HotListController.$inject = ['HotList', 'HotListSearch'];

  function HotListController(HotList, HotListSearch) {

    var vm = this;

    vm.hotLists = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      HotList.query(function (result) {
        vm.hotLists = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      HotListSearch.query({query: vm.searchQuery}, function (result) {
        vm.hotLists = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
