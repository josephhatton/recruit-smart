(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantStatusController', ApplicantStatusController);

  ApplicantStatusController.$inject = ['ApplicantStatus', 'ApplicantStatusSearch'];

  function ApplicantStatusController(ApplicantStatus, ApplicantStatusSearch) {

    var vm = this;

    vm.applicantStatuses = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      ApplicantStatus.query(function (result) {
        vm.applicantStatuses = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      ApplicantStatusSearch.query({query: vm.searchQuery}, function (result) {
        vm.applicantStatuses = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
