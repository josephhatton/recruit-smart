(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('search', {
        parent: 'entity',
        url: '/search',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'Search'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/search-list/search.html',
            controller: 'SearchController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('search-list', {
        parent: 'entity',
        url: '/search-list',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'SearchLists'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/search-list/search-lists.html',
            controller: 'SearchListController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('search-list-detail', {
        parent: 'entity',
        url: '/search-list/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'SearchList'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/search-list/search-list-detail.html',
            controller: 'SearchListDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'SearchList', function ($stateParams, SearchList) {
            return SearchList.get({id: $stateParams.id});
          }]
        }
      })
      .state('search-list.new', {
        parent: 'search-list',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/search-list/search-list-dialog.html',
            controller: 'SearchListDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  type: null,
                  query: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('search-list', null, {reload: true});
          }, function () {
            $state.go('search-list');
          });
        }]
      })
      .state('search-list.edit', {
        parent: 'search-list',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/search-list/search-list-dialog.html',
            controller: 'SearchListDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['SearchList', function (SearchList) {
                return SearchList.get({id: $stateParams.id});
              }]
            }
          }).result.then(function () {
            $state.go('search-list', null, {reload: true});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('search-list.delete', {
        parent: 'search-list',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/search-list/search-list-delete-dialog.html',
            controller: 'SearchListDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['SearchList', function (SearchList) {
                return SearchList.get({id: $stateParams.id});
              }]
            }
          }).result.then(function () {
            $state.go('search-list', null, {reload: true});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
