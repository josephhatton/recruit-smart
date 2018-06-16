(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('CompanyCommentController', CompanyCommentController);

  CompanyCommentController.$inject = ['CompanyComment', 'CompanyCommentSearch'];

  function CompanyCommentController(CompanyComment, CompanyCommentSearch) {

    var vm = this;

    vm.companyComments = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      CompanyComment.query(function (result) {
        vm.companyComments = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      CompanyCommentSearch.query({query: vm.searchQuery}, function (result) {
        vm.companyComments = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
