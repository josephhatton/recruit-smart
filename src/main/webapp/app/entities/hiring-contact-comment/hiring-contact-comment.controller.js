(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactCommentController', HiringContactCommentController);

  HiringContactCommentController.$inject = ['HiringContactComment', 'HiringContactCommentSearch'];

  function HiringContactCommentController(HiringContactComment, HiringContactCommentSearch) {

    var vm = this;

    vm.hiringContactComments = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      HiringContactComment.query(function (result) {
        vm.hiringContactComments = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      HiringContactCommentSearch.query({query: vm.searchQuery}, function (result) {
        vm.hiringContactComments = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
