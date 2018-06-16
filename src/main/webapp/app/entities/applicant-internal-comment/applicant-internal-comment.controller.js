(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantInternalCommentController', ApplicantInternalCommentController);

  ApplicantInternalCommentController.$inject = ['ApplicantInternalComment', 'ApplicantInternalCommentSearch'];

  function ApplicantInternalCommentController(ApplicantInternalComment, ApplicantInternalCommentSearch) {

    var vm = this;

    vm.applicantInternalComments = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      ApplicantInternalComment.query(function (result) {
        vm.applicantInternalComments = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      ApplicantInternalCommentSearch.query({query: vm.searchQuery}, function (result) {
        vm.applicantInternalComments = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
