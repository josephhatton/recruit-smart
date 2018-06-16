(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantCommentController', ApplicantCommentController);

  ApplicantCommentController.$inject = ['ApplicantComment', 'ApplicantCommentSearch'];

  function ApplicantCommentController(ApplicantComment, ApplicantCommentSearch) {

    var vm = this;

    vm.applicantComments = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      ApplicantComment.query(function (result) {
        vm.applicantComments = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      ApplicantCommentSearch.query({query: vm.searchQuery}, function (result) {
        vm.applicantComments = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
