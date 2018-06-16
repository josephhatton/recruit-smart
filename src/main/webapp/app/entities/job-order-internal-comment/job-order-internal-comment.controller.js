(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderInternalCommentController', JobOrderInternalCommentController);

  JobOrderInternalCommentController.$inject = ['JobOrderInternalComment', 'JobOrderInternalCommentSearch'];

  function JobOrderInternalCommentController(JobOrderInternalComment, JobOrderInternalCommentSearch) {

    var vm = this;

    vm.jobOrderInternalComments = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      JobOrderInternalComment.query(function (result) {
        vm.jobOrderInternalComments = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      JobOrderInternalCommentSearch.query({query: vm.searchQuery}, function (result) {
        vm.jobOrderInternalComments = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
