(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ActivityActionController', ActivityActionController);

  ActivityActionController.$inject = ['ActivityAction', 'ActivityActionSearch'];

  function ActivityActionController(ActivityAction, ActivityActionSearch) {

    var vm = this;

    vm.activityActions = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      ActivityAction.query(function (result) {
        vm.activityActions = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      ActivityActionSearch.query({query: vm.searchQuery}, function (result) {
        vm.activityActions = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
