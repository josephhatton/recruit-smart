(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HotListBucketController', HotListBucketController);

  HotListBucketController.$inject = ['HotListBucket', 'HotListBucketSearch'];

  function HotListBucketController(HotListBucket, HotListBucketSearch) {

    var vm = this;

    vm.hotListBuckets = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      HotListBucket.query(function (result) {
        vm.hotListBuckets = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      HotListBucketSearch.query({query: vm.searchQuery}, function (result) {
        vm.hotListBuckets = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
