(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderCommentController', JobOrderCommentController);

  JobOrderCommentController.$inject = ['JobOrderComment', 'JobOrderCommentSearch'];

  function JobOrderCommentController(JobOrderComment, JobOrderCommentSearch) {

    var vm = this;

    vm.jobOrderComments = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      JobOrderComment.query(function (result) {
        vm.jobOrderComments = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      JobOrderCommentSearch.query({query: vm.searchQuery}, function (result) {
        vm.jobOrderComments = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
