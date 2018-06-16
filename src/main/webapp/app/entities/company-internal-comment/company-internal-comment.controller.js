(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('CompanyInternalCommentController', CompanyInternalCommentController);

  CompanyInternalCommentController.$inject = ['CompanyInternalComment', 'CompanyInternalCommentSearch'];

  function CompanyInternalCommentController(CompanyInternalComment, CompanyInternalCommentSearch) {

    var vm = this;

    vm.companyInternalComments = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      CompanyInternalComment.query(function (result) {
        vm.companyInternalComments = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      CompanyInternalCommentSearch.query({query: vm.searchQuery}, function (result) {
        vm.companyInternalComments = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
