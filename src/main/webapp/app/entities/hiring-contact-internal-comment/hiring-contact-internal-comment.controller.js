(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactInternalCommentController', HiringContactInternalCommentController);

  HiringContactInternalCommentController.$inject = ['HiringContactInternalComment', 'HiringContactInternalCommentSearch'];

  function HiringContactInternalCommentController(HiringContactInternalComment, HiringContactInternalCommentSearch) {

    var vm = this;

    vm.hiringContactInternalComments = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      HiringContactInternalComment.query(function (result) {
        vm.hiringContactInternalComments = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      HiringContactInternalCommentSearch.query({query: vm.searchQuery}, function (result) {
        vm.hiringContactInternalComments = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
